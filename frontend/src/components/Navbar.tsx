import React, {useState} from 'react';
import '../styles/NavBar.css';

const NavBar = () => {
    const [selectArea, setSelectArea] = useState('도');
    const areas = ['도', '시', '동'];

    const handleAreaClick = (area: string) => {
        setSelectArea(area);
    };

    return (
        <header className="navbar">
            <h1 className="navbar-title">사이트명</h1>
            <nav className="navbar-menu">
                {areas.map((area) => (
                    <button key={area} onClick={() =>
                        handleAreaClick(area)}
                            className={selectArea === area ? 'selected' : ''}
                    >
                        {area}
                    </button>
                ))}
            </nav>
            <div className="login">login</div>
        </header>
    );
};

export default NavBar;
