import pandas as pd
from gensim.models import Word2Vec
from sklearn.ensemble import RandomForestRegressor
from sklearn.model_selection import train_test_split
import numpy as np

# 1. Загрузка и предобработка данных
# Здесь вы можете загрузить данные из вашего исходного датафрейма и провести необходимую предобработку (очистка, токенизация, лемматизация и т.д.)
data = pd.read_csv("event.csv")



# 3. Создание векторных представлений текстов
# Здесь вы можете использовать обученную модель Word2Vec для создания векторных представлений текстов
# Пример:
title_vectors = []
description_vectors = []
for title, description in zip(titles, descriptions):
    title_vector = np.mean([model.wv[word] for word in title], axis=0)
    description_vector = np.mean([model.wv[word] for word in description], axis=0)
    title_vectors.append(title_vector)
    description_vectors.append(description_vector)

# 4. Создание нового датафрейма
# Здесь вы можете создать новый датафрейм, который будет содержать векторные представления текстов и остальные признаки
# Пример:
new_df = pd.DataFrame({
    'title': title_vectors,
    'description': description_vectors,
    'start_date': start_dates,
    'end_date': end_dates,
    'participants': participants
})

# 5. Разделение на обучающую и тестовую выборки
# Здесь вы можете разделить новый датафрейм на обучающую и тестовую выборки
# Пример:
X = new_df.drop(['start_date'], axis=1)
y = new_df['start_date']
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)

# 6. Обучение модели Random Forest
# Здесь вы можете использовать X_train и y_train для обучения модели Random Forest
# Пример:
rf_model = RandomForestRegressor(n_estimators=100, random_state=42)
rf_model.fit(X_train, y_train)

# 7. Оценка модели
# Здесь вы можете использовать X_test и y_test для оценки качества модели
# Пример:
y_pred = rf_model.predict(X_test)
score = rf_model.score(X_test, y_test)
