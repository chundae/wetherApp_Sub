import React from 'react';
import '../styles/WeatherSection.css'

interface WeatherSectionProps {
    title: string;
    details: string[];
}


const WeatherSection = ({ title, details} : WeatherSectionProps) => {
    return (
        <div className="weather-section">
            <h2>{title}</h2>
            <ul>
                {details.map((details, index) =>(
                <li key={index}>{details}</li>
                ))}
            </ul>
        </div>
    );
};

export default WeatherSection;