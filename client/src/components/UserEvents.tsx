import React, {useEffect, useState} from 'react';
import axios from "axios";
import {Formik, Form, Field} from "formik";
import 'bootstrap/dist/css/bootstrap.min.css';

interface User {
    id: number;
    firstName: string;
    lastName: string;
    email: string;
}

interface Event {
    id: string;
    title: string;
    description: string;
    emotionalCondition: number,
    datEmotionalCondition: number,
    predictedDayEmotionalCondition: number,
    predictedEmotionalCondition: number,
    startDate: string,
    endDate: string,
    email: string;
}

interface GetUserProps {
    apiUrl: string;
    currentUserId: number;
}

const GetUser: React.FC<GetUserProps> = ({apiUrl, currentUserId}) => {
    // const [userId, setUserId] = useState<number>(0);
    const [date, setDate] = useState<string>();
    // const [user, setUser] = useState<User | null>(null);
    const [event, setEvent] = useState<Event | null>(null);
    const [events, setEvents] = useState<Event[]>([]);
    // const [events, setEvents] = useState([]);

    useEffect(() => {
        axios.get<Event[]>(`${apiUrl}/event/${currentUserId}/`)
            .then((response) => {
                setEvents(response.data);
            })
            .catch((error: any) => {
                console.log(error);
            });
    }, []);
    const handleSubmit = (values: {date: any; }) => {

        try {
            const {date} = values;

            axios.get<Event[]>(`${apiUrl}/event/${currentUserId}/${date}`)
                .then((response) => {
                    setEvents(response.data);
                })
                .catch((error: any) => {
                    console.log(error);
                });
        } catch (error) {
            console.error('Error getting user:', error);
        }
    };

    return (
        <>
            <div className="container mt-5">
                <Formik
                    initialValues={{userId: '', date: ''}}
                    onSubmit={handleSubmit}
                >
                    {() => (
                        <Form>
                            {/*<label htmlFor="userId">User ID:</label>*/}
                            {/*<Field type="text" name="userId"/>*/}

                            <label htmlFor="date">Creation Date:</label>
                            <Field type="date" name="date"/>

                            <button type="submit">
                                Submit
                            </button>
                        </Form>
                    )}
                </Formik>
                <table className="table mt-3">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Title</th>
                        <th>Description</th>
                        <th>Start Date</th>
                        <th>End Data</th>
                        <th>Real Emotional Condition</th>
                        <th>Predicted Emotional Condition</th>
                    </tr>
                    </thead>
                    <tbody>
                    {events.map((event) => (
                        <tr key={event.id}>
                            <td key={event.id}></td>
                            <td>{event.title}</td>
                            <td>{event.description}</td>
                            <td>{event.startDate}</td>
                            <td>{event.endDate}</td>
                            <td>{event.emotionalCondition}</td>
                            <td>{event.predictedEmotionalCondition}</td>
                        </tr>
                    ))}
                    </tbody>

                </table>
            </div>
        </>
    )
        ;
};

export default GetUser;
