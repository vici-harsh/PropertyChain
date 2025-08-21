import React from 'react';

function Services() {
  return (
    <section id="services" className="services">
      <div className="container">
        <div className="row">
          <div className="col-lg-4 col-md-6">
            <div className="icon-box">
              <div className="icon"><i className="fas fa-heart"></i></div>
              <h4 className="title"><a href="">Nesciunt Mete</a></h4>
              <p className="description">Provident nihil minus qui consequatur non omnis maiores.</p>
            </div>
          </div>
          <div className="col-lg-4 col-md-6">
            <div className="icon-box">
              <div className="icon"><i className="fas fa-bar-chart"></i></div>
              <h4 className="title"><a href="">Eosle Commodi</a></h4>
              <p className="description">Ut autem aut autem non a. Sint sint sit facilis nam iusto sint.</p>
            </div>
          </div>
          // Add more icon-boxes from the HTML
        </div>
      </div>
    </section>
  );
}

export default Services;