# EmoTrack

### Description

This application is a part of the final qualifying work "A system for predicting the psycho-emotional state based on the analysis of diary entries".

This is for people who constantly keep a diary or record of events and want to know how the planned events will affect their condition.

### How it's work

1) User insert entries in the system and fills in the field `emotional condition'
2) User add planned event
3) The system gives its assessment of the psycho-emotional state according to this record

### Technologies

MySQL, JDK 11, Spring Boot, React, OpenAI, LiquiBase, Gradle, MapStract, Python 3.9, sklearn (Random Forest), gensim (Векторизация), Kafka

#### Algorithms
Random Forest, word2vec


### Application part manual

There are 4 components of this application:
  1) EmoTrack - this is backend web part.
  2) EmoTrackRegressionSystem - this is system, that use machine learning models to predict the state
  3) OpenAiConnect - the system, that was use OpenAI API to generate training dataset
  4) client - clieтt web app part
