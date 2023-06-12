from kafka import KafkaProducer, KafkaConsumer
import threading

# Конфигурация брокера Kafka
bootstrap_servers = "localhost:9092"
topic = "my_topic"
toPredictQueue = "to_predict_queue"
predictedQueue = "predicted_queue"

# Создание продюсера Kafka
producer = KafkaProducer(bootstrap_servers=bootstrap_servers)

# Функция для отправки сообщений
def send_message(message):
    producer.send(predictedQueue, message.encode("utf-8"))
    producer.flush()

# Создание потребителя Kafka
consumer = KafkaConsumer(toPredictQueue, bootstrap_servers=bootstrap_servers)

# Функция для чтения сообщений
def read_messages():
    for message in consumer:
        print(message.value.decode("utf-8"))

# Функция, выполняющая отправку сообщений в отдельном потоке
def send_messages_thread():
    while True:
        message = input("Введите сообщение: ")
        # mess = "{ \"id\": 123, \"tittle\": \"Breakfest\", \"description\": \"I have  a good breakfast\", \"start_date\": \"2023-04-30 14:22:00\", \"end_date\": \"2023-05-01 18:23:00\"}"
        mess = "{ \"id\": " + str(message) + ", \"value\": 7}"
        send_message(mess)

# Функция, выполняющая чтение сообщений в отдельном потоке
def read_messages_thread():
    read_messages()


if __name__ == "__main__":
    # Создание и запуск потока для отправки сообщений
    send_thread = threading.Thread(target=send_messages_thread)
    send_thread.start()

    # Создание и запуск потока для чтения сообщений
    read_thread = threading.Thread(target=read_messages_thread)
    read_thread.start()
