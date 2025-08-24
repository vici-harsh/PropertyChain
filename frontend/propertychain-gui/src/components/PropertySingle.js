import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { getPropertyHistory, getAllProperties } from '../services/api';
import { Swiper, SwiperSlide } from 'swiper/react';
import { Navigation, Pagination } from 'swiper/modules';

function PropertySingle({ account }) {
  const { id } = useParams();
  const [history, setHistory] = useState([]);
  const [property, setProperty] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const historyRes = await getPropertyHistory(id);
        setHistory(historyRes.data || []);

        const propertiesRes = await getAllProperties();
        const prop = propertiesRes.data.find(p => p.id === parseInt(id));
        setProperty(prop);
      } catch (err) {
        console.error(err);
      } finally {
        setLoading(false);
      }
    };
    fetchData();
  }, [id]);

  if (loading) return <p>Loading...</p>;

  return (
    <section id="property-single" className="property-single">
      <div className="container">
        <div className="section-title">
          <h2>Property Single</h2>
          <p>Odio et unde deleniti...</p>
        </div>
        <div className="row">
          <div className="col-lg-8">
            <Swiper
              navigation
              pagination={{ clickable: true }}
              modules={[Navigation, Pagination]}
            >
              <SwiperSlide><img src="../assets/img/property-single/property-single-1.jpg" alt="" /></SwiperSlide>
              {/* Add slides */}
            </Swiper>
            <div className="property-description">
              <p className="description">Autem ipsum nam porro corporis rerum...</p>
              {/* Add more content */}
            </div>
            {/* Tabs for Video, Floor Plans, Location */}
            <ul className="nav nav-pills">
              <li className="nav-item"><a className="nav-link active" href="#video">Video</a></li>
              <li className="nav-item"><a className="nav-link" href="#floor">Floor Plans</a></li>
              <li className="nav-item"><a className="nav-link" href="#location">Location</a></li>
            </ul>
            <div className="tab-content">
              {/* Tab panes */}
            </div>
          </div>
          <div className="col-lg-4">
            <div className="sidebar">
              <h3>Quick Summary</h3>
              <ul>
                <li>Property ID: {id}</li>
                <li>Location: {property?.address}</li>
                {/* Add from template */}
              </ul>
            </div>
          </div>
        </div>
        <h3>History</h3>
        <ul>{history.map((h, i) => <li key={i}>{h}</li>)}</ul>
      </div>
    </section>
  );
}

export default PropertySingle;