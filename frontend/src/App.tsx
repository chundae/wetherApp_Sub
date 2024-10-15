import logo from './logo.svg';
import './App.css';
import React, {useState} from 'react';
import axios, {AxiosResponse} from "axios"
import NavBar from "./components/Navbar";
import WeatherSection from "./components/WeatherSection";
import WeeklyForecast from "./components/WeeklyForecast";
import ActivitySuggestion from "./components/ActivitySuggestion";

function App() {
   return(
       <div className="app">
           <NavBar/>
           <div className="main-content">
               <div className="weather-sections">
                   <WeatherSection title="현재기온" details={['온도', '습도','풍속']}/>
                   <WeatherSection title="24시간온도" details={['현재온도', '최저 / 최고 온도']}/>
               </div>
               <WeeklyForecast/>
               <ActivitySuggestion/>
           </div>
       </div>
   );
}

export default App;
