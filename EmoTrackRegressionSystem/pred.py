# -*- coding: utf-8 -*-
import joblib
import numpy as np
import pandas as pd
import gensim.downloader as api
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


def predict(data):
    # data['tittle'] = data['title']
    # data.drop('title')
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
    # data = [
    #     {'tittle': 'Barbecue',
    #      'description': 'Had a barbecue with family'
    #                     '', 'start_date': '2020-01-10 19:00:00', 'end_date':
    #          '2020-01-10 22:00:00'},
    #     {'tittle': 'Meeting About Project XYZ',
    #      'description': 'Met with the team to discuss potential solutions for '
    #                     'project XYZ', 'start_date': '2020-01-01 12:00:00', 'end_date':
    #          '2020-01-01 13:00:00'},
    #     {'tittle': 'Played Basketball with Friends',
    #      'description': 'Played basketball with friends at the park'
    #                     '', 'start_date': '2021-03-11 16:00:00', 'end_date':
    #          '2021-03-11 17:30:00'}
    # ]

    data = [
        {'tittle': 'Breakfest',
         'description': 'I have  a good breakfast',
         'start_date': '2023-04-30 14:22:00',
         'end_date': '2023-05-01 18:23:00'}

    ]
    df = pd.DataFrame(data)
    y_pred = predict(df)
    print(y_pred)
