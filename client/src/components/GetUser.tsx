import React, { useState } from 'react';

interface User {
    id: number;
    firstName: string;
    lastName: string;
    email: string;
}

interface GetUserProps {
    apiUrl: string;
}

const GetUser: React.FC<GetUserProps> = ({ apiUrl }) => {
    const [userId, setUserId] = useState<number>(0);
    const [user, setUser] = useState<User | null>(null);

    const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();

        try {
            const response = await fetch(`${apiUrl}/user/${userId}`);

            if (response.ok) {
                const data = await response.json();
                setUser(data);
            } else {
                console.error('Error getting user:', response.status);
            }
        } catch (error) {
            console.error('Error getting user:', error);
        }
    };

    return (
        <div>
            <h2>Get User</h2>
            <form onSubmit={handleSubmit}>
                <div>
                    <label htmlFor="userId">User ID:</label>
                    <input
                        type="number"
                        id="userId"
                        value={userId}
                        onChange={(event) => setUserId(Number(event.target.value))}
                    />
                </div>
                <button type="submit">Get User</button>
            </form>
            {user && (
                <div>
                    <h3>User Details</h3>
                    <p>ID: {user.id}</p>
                    <p>First Name: {user.firstName}</p>
                    <p>Last Name: {user.lastName}</p>
                    <p>Email: {user.email}</p>
                </div>
            )}
        </div>
    );
};

export default GetUser;
