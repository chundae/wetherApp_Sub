import React from "react";
import '../styles/ActivitySuggestion.css'

const ActivitySuggestion = () => {
    return (
        <div className="activity-suggestions">
            <h2>활동 추천</h2>
            <div className="activities">
                <div className="activity">1</div>
                <div className="activity">2</div>
                <div className="activity">3</div>
            </div>
        </div>
    );
};

export default ActivitySuggestion;