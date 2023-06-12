# -*- coding: utf-8 -*-
import joblib
import numpy as np
import pandas as pd
import gensim.downloader as api
from kafka import KafkaProducer, KafkaConsumer
import threading
import json

# Конфигурация брокера Kafka
bootstrap_servers = 'localhost:9092'
toPredictQueue = 'to_predict_queue'
predictedQueue = 'predicted_queue'

# Создание продюсера Kafka
producer = KafkaProducer(bootstrap_servers=bootstrap_servers)

# Функция для отправки сообщений
def send_message(message):
    producer.send(predictedQueue, message.encode('utf-8'))
    producer.flush()
    print("Value send:", message)

# Создание потребителя Kafka
consumer = KafkaConsumer(toPredictQueue, bootstrap_servers=bootstrap_servers)

# Функция для чтения сообщений
def read_messages():
    for message in consumer:
        print("New message:", message)
        json_val = json.loads(message.value.decode("utf-8"))
        df = pd.DataFrame(json_val, index=[0]).drop(['id'], axis=1)
        index = json_val['id']
        y_pred = predict(df)
        mes = '{\"id\": ' + str(index) + ', ' + '\"value\": ' + str(y_pred) + '}'
        send_message(mes)


# Функция, выполняющая чтение сообщений в отдельном потоке
def read_messages_thread():
    read_messages()

def get_sentence_vectors(df, column_name):
    model = api.load("word2vec-google-news-300")

    sentence_vectors = []
    for sentence in df[column_name]:
        words = sentence.split()
        vectors = []
        for word in words:
            if word in model:
                vectors.append(model[word])
        if vectors:
            sentence_vectors.append(np.mean(vectors, axis=0))
        else:
            sentence_vectors.append(np.zeros(300))  # или любую другую длину вектора

    return sentence_vectors


def predict(data):
    data['description'] = data['description'].fillna('')
    data['tittle'] = data['tittle'].fillna('')
    # data.fillna(data.mean(), inplace=True)

    X = data

    description_vector = get_sentence_vectors(X, 'description')
    description_vector_df = pd.DataFrame(description_vector, columns=['description_' + str(i) for i in range(300)])
    tittle_vector = get_sentence_vectors(X, 'tittle')
    tittle_vector_df = pd.DataFrame(tittle_vector, columns=['tittle_' + str(i) for i in range(300)])

    X = X.drop(['tittle', 'description'], axis=1)

    start_date_vector = get_sentence_vectors(X, 'start_date')
    start_date_vector_df = pd.DataFrame(start_date_vector, columns=['start_date_' + str(i) for i in range(300)])
    end_date_vector = get_sentence_vectors(X, 'end_date')
    end_date_vector_df = pd.DataFrame(end_date_vector, columns=['end_date_' + str(i) for i in range(300)])
    X = X.drop(['start_date', 'end_date'], axis=1)

    X = pd.concat([X, tittle_vector_df, description_vector_df, start_date_vector_df, end_date_vector_df], axis=1)
    loaded_model = joblib.load('model.joblib')
    y_pred = loaded_model.predict(X)
    return y_pred

if __name__ == '__main__':

    # Создание и запуск потока для чтения сообщений
    read_thread = threading.Thread(target=read_messages_thread)
    read_thread.start()
