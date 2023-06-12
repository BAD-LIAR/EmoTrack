import pandas as pd
from gensim.models import Word2Vec
import numpy as np
from sklearn.ensemble import RandomForestClassifier

sentences = [["cat", "say", "meow"], ["dog", "say", "woof"]]
model = Word2Vec(sentences, vector_size=100, window=5, min_count=1, workers=4)


def get_vectors(model, sentences):
    vectors = []
    for sentence in sentences:
        vectors.append(np.mean([model.wv[word] for word in sentence if word in model.wv], axis=0))
    return np.array(vectors)


X_train = get_vectors(model, sentences)
y_train = [0, 1]  # метки классов

clf = RandomForestClassifier(n_estimators=100, random_state=42)
clf.fit(X_train, y_train)

new_sentence = ["cat", "say", "meow"]
new_vector = np.mean([model.wv[word] for word in new_sentence if word in model.wv], axis=0)
clf.predict([new_vector])

df = pd.DataFrame(X_train)
print(df)
