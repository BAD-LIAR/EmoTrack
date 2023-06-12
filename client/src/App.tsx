import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import CreateUser from './components/CreateUser';
import GetUser from './components/GetUser';
import CreateEvent from './components/CreateEvent';
import UserEvents from "./components/UserEvents";
import 'bootstrap/dist/css/bootstrap.min.css';


const apiUrl = 'http://localhost:8080';

const App: React.FC = () => {
  return (
      <Router>
        <div>
          <nav className="navbar navbar-expand-lg navbar-light bg-light">
            <ul className="navbar-nav me-auto mb-2 mb-lg-0">
              <li className="nav-item">
                <a className="nav-link active" aria-current="page" href="/create-user">Create User</a>
              </li>
              <li className="nav-item">
                <a className="nav-link active" aria-current="page" href="/get-user">Get User</a>
              </li>
              <li className="nav-item">
                <a className="nav-link active" aria-current="page" href="/create-event">Create Event</a>
              </li>
              <li className="nav-item">
                <a className="nav-link active" aria-current="page" href="/view-events">View Event</a>
              </li>
            </ul>
          </nav>
          <Routes>
            <Route path="/create-user" element={<CreateUser apiUrl={apiUrl}/>} />
            <Route path="/get-user" element={<GetUser apiUrl={apiUrl}/>} />
            <Route path="/create-event" element={<CreateEvent apiUrl={apiUrl} currentUserId={2}/>} />
            <Route path="/view-events" element={<UserEvents apiUrl={apiUrl} currentUserId={2}/>} />
          </Routes>
        </div>
      </Router>
  );
};

export default App;