import gensim.downloader as api
import joblib
import numpy as np
import pandas as pd
from sklearn.ensemble import RandomForestClassifier
from sklearn.metrics import accuracy_score
from sklearn.model_selection import train_test_split
pd.set_option('display.max_columns', None)


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


def generate_model():
    # Загрузка данных
    data = pd.read_csv("event.csv")

    # Удаление ненужных столбцов и преобразование данных
    data['description'] = data['description'].fillna('')
    data['tittle'] = data['tittle'].fillna('')
    data.fillna(data.mean(), inplace=True)

    X = data.drop(['emotional_condition', 'day_emotional_condition', 'id', 'user_id'], axis=1)

    # # Пример использования функции
    # description = "This is a sample sentence"
    # vector = sentence_vectorizer(description)
    # print(vector)

    # X = pd.get_dummies(data, columns=['tittle', 'description', 'start_date', 'end_date'])
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

    # print(X)
    # X.to_excel('X.xlsx', index=False)
    # X = pd.get_dummies(X, columns=['start_date', 'end_date'])
    print(X.columns)

    y = data['day_emotional_condition']

    # Разделение на обучающий и тестовый наборы
    X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2)

    # Создание и обучение модели
    clf = RandomForestClassifier(n_estimators=100, max_depth=10)
    clf.fit(X_train, y_train)

    # Прогнозирование на тестовом наборе
    y_pred = clf.predict(X_test)

    # Оценка точности модели
    accuracy = accuracy_score(y_test, y_pred)
    print(f"Точность модели: {accuracy}")

    predictions = clf.predict(X_test)
    for i, prediction in zip(X_test.index, predictions):
        print(f"Запись {i}: {prediction}")

    joblib.dump(clf, 'model.joblib')


if __name__ == '__main__':
    generate_model()
