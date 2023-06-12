import React, {useState} from 'react';
import {Field, Form, Formik} from "formik";
import axios from "axios";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import { useNavigate } from "react-router-dom";




interface Event {
    id: number;
    title: string;
    description: string;
    startDate: string;
    endDate: string
    userId: number;
}

interface CreateEventProps {
    apiUrl: string;
    currentUserId: number;
}

const DateTimePicker = () => {
    const [startDate, setStartDate] = useState(new Date());
    return (
        <DatePicker
            onChange={(date) => {
                if (date)
                    setStartDate(date)
                else
                    console.log('ups')
            }
            }
            showTimeSelect
            timeFormat="p"
            timeIntervals={15}
            // dateFormat="Pp"
            dateFormat="dd MM yyyy"
        />
    );
};

const CreateEvent: React.FC<CreateEventProps> = ({apiUrl, currentUserId}) => {
    const [title, setTitle] = useState<string>('');
    const [description, setDescription] = useState<string>('');
    const [startDate, setStartDate] = useState<string>('');
    const [endDate, setEndDate] = useState<string>('');
    const navigate = useNavigate();

    const handleSubmit = async (values: { title: any; description: any; startDate: any; endDate: any; startTime: any; endTime: any; }) => {
        const {title, description, startDate, endDate, startTime, endTime} = values;

        try {
            axios({
                method: 'post',
                url: `${apiUrl}/event/create`,
                headers: {'Content-Type': 'application/json',},
                data: {
                    'title': title,
                    'description': description,
                    'startDate': startDate + ' '  + startTime,
                    'endDate': endDate + ' '  + endTime,
                    'userId': currentUserId
                }
            }).catch((error: any) => {
                    console.log(error);
                });
            navigate("/");
        } catch (error) {
            console.error('Error creating event:', error);
        }
    };

    return (
        <div>
            <h2>Create Event</h2>
            <Formik
                initialValues={{title: '', description: '', startDate: '', endDate: '', startTime: '', endTime: ''}}
                onSubmit={handleSubmit}
            >
                {() => (
                    <Form>
                        <label htmlFor="eventTitle">Title:</label>
                        <Field type="text" name="title"/>

                        <label htmlFor="eventDescription">Description:</label>
                        <Field type="text" name="description"/>

                        <label htmlFor="eventStartDate">Start Date:</label>
                        <Field type="date" name="startDate">
                            {/*{({ field }: { field: { name: string, value: any } }) => (*/}
                            {/*    <DatePicker*/}
                            {/*        dateFormat="dd MM yyyy"*/}
                            {/*    />*/}
                            {/*)}*/}
                        </Field>
                        <label htmlFor="eventStartTime">Start Time:</label>
                        <Field type="time" name="startTime"/>

                        <br></br>
                        <label htmlFor="eventEndDate">End Date:</label>
                        <Field type="date" name="endDate"/>
                        <label htmlFor="eventEndTime">End Time:</label>
                        <Field type="time" name="endTime"/>
                        <button type="submit">
                            Submit
                        </button>
                    </Form>
                )}
            </Formik>
        </div>
    );
};

export default CreateEvent;