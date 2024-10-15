import logo from './logo.svg';
import './App.css';
import React, {useState} from 'react';
import axios, {AxiosResponse} from "axios"

function App() {
    const [message, setMessage] = useState('');

    const responseHandler = ({data}: AxiosResponse<string>) => {
        setMessage(data);
        return data;
    };

    const errorHandler = (error : {message: string}) => {
        setMessage(error.message);
        return error.message;
    };

    const onNonCorsHeaderHandler = () => {
        axios.get('http://localhost:8080/not-cors')
            .then(responseHandler)
            .catch(errorHandler);
    };

    const onCorsHeaderHandler = () => {
        axios.get('http://localhost:8080/cors')
            .then(responseHandler)
            .catch(errorHandler);
    };

    const onNonProxyHandler = () => {
        axios.get('/not-proxy')
            .then(responseHandler)
            .catch(errorHandler);
    };

    const onProxyHandler = () => {
        axios.get('/proxy')
            .then(responseHandler)
            .catch(errorHandler);
    };

    return (
        <div>
            <p>
                {message}
            </p>

            <div>
                <button onClick={onNonCorsHeaderHandler}>non cors header</button>
                <button onClick={onCorsHeaderHandler}>cors header</button>
                <button onClick={onNonProxyHandler}>nonProxy</button>
                <button onClick={onProxyHandler}>proxy</button>
            </div>
        </div>
    );
}

export default App;
