import api from "../../api";
import {useEffect, useState} from "react";
import '../../assets/css/Content.css';
import moment from "moment";

export default function Content(){
    const [adverts, setAdverts] = useState([]);

    useEffect(() => {
        fetchAdverts();
    }, [])

    const fetchAdverts = async () => {
        const response = await api.get("");
        setAdverts(response.data);
        console.log(response)
    }

    const advertList = adverts.map((advert) => {
        return (<section className="advert-card">
            <div className="advert-image">
                <img src={`data:${advert.advertImage.type};base64,${advert.advertImage.imageData}`} alt={advert.title}/>
            </div>
            <div className="advert-info">
                <div className="advert-price">
                    {(advert.price).toLocaleString('en-US',{maximumFractionDigits:2})}
                </div>
                <h3 className="advert-title">
                    {advert.title}
                </h3>
                <div className="secondary-info">
                    <div className="advert-location">
                        {advert.location}
                    </div>
                    <div className="advert-published">
                        {moment(advert.createdAt).format('DD-MM-YYYY HH:mm')}
                    </div>
                </div>
            </div>
        </section>)
    });

    return(
        <div className="adverts">
            {advertList}
        </div>
    );
}