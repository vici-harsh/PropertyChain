import React from 'react';
import { Swiper, SwiperSlide } from 'swiper/react';
import { Navigation, Pagination, Autoplay } from 'swiper/modules';
import 'swiper/css';
import 'swiper/css/navigation';
import 'swiper/css/pagination';
import { Link } from 'react-router-dom';

function Hero() {
  return (
    <section id="hero" className="hero d-flex align-items-center">
      <div className="container">
        <div className="row">
          <div className="col-lg-6 d-flex flex-column justify-content-center">
            <h1 data-aos="fade-up">We offer modern solutions for growing your business</h1>
            <h2 data-aos="fade-up" data-aos-delay="400">We are team of talented designers making websites with Bootstrap</h2>
            <div data-aos="fade-up" data-aos-delay="600">
              <div className="text-center text-lg-start">
                <Link to="/properties" className="btn-get-started scrollto d-inline-flex align-items-center justify-content-center align-self-center">
                  <span>Get Started</span>
                  <i className="bi bi-arrow-right"></i>
                </Link>
              </div>
            </div>
          </div>
          <div className="col-lg-6 hero-img" data-aos="zoom-out" data-aos-delay="200">
            <Swiper
              className="hero-carousel"
              loop={true}
              autoplay={{ delay: 5000, disableOnInteraction: false }}
              navigation={{
                nextEl: '.swiper-button-next',
                prevEl: '.swiper-button-prev',
              }}
              pagination={{ el: '.swiper-pagination', clickable: true }}
              modules={[Navigation, Pagination, Autoplay]}
            >
              <SwiperSlide><img src={require('../assets/img/hero-carousel/hero-carousel-1.jpg')} className="img-fluid" alt="Slide 1" /></SwiperSlide>
              <SwiperSlide><img src={require('../assets/img/hero-carousel/hero-carousel-2.jpg')} className="img-fluid" alt="Slide 2" /></SwiperSlide>
              <SwiperSlide><img src={require('../assets/img/hero-carousel/hero-carousel-3.jpg')} className="img-fluid" alt="Slide 3" /></SwiperSlide>
            </Swiper>
          </div>
        </div>
      </div>
    </section>
  );
}

export default Hero;