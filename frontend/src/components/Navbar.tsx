import React, {useState} from 'react';
import axios from 'axios';
import '../styles/NavBar.css';

const NavBar = () => {
    const [selectArea, setSelectArea] = useState('도');
    const [regions, setRegions] = useState<string[]>([]);
    const [subRegions, setSubRegions] = useState<string[]>([]);
    const [selectedRegion, setSelectedRegion] = useState<string | null>(null);

    const handleAreaClick = (area: string) => {
        setSelectArea(area);
        fetchRegions(area);
    };

    const fetchRegions = async (area: string) => {
        try {
            const response = await axios.get(`/api/regions?area=${area}`);
            setRegions(response.data);
        } catch (error){
            console.error("Error fetching regions: ", error);
        }
    };

    const handleRegionSelect = async (region: string) => {
        setSelectedRegion(region);
        try {
            const response = await axios.get(`/api/subRegions?region=${region}`);
            setSubRegions(response.data);
        } catch (error){
            console.error("Error fetching sub-region: ", error);
        }
    };


    return (
        <header className="navbar">

            <nav className="navbar-menu">
                {['도', '시', '동'].map((area) =>(
                    <button
                    key={area}
                    onClick={()=> handleAreaClick(area)}
                    className={selectArea === area ? 'selected' : ''}
                    >
                        {area}
                    </button>
                ))}
            </nav>
            <div className="login">login</div>

            {selectedRegion && (
                <div className="modal">
                    <h2>{selectedRegion}</h2>
                    <div className="sub-regions">
                        {subRegions.map((subRegion) => (
                            <button
                            key={subRegion}
                            onClick={()=> console.log(`Selected ${subRegion}`)}
                            >
                                {subRegion}
                            </button>
                        ))}
                    </div>
                    <button onClick={()=> setSelectedRegion(null)}>Close</button>
                </div>
            )}
        </header>
    );
};

export default NavBar;
