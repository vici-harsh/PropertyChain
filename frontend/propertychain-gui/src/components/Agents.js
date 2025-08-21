import React from 'react';

function Agents() {
  return (
    <section id="agents" className="agents section-bg">
      <div class="container">
        <div class="section-title">
          <h2>Agents</h2>
          <p>Odio et unde deleniti. Deserunt numquam exercitationem.</p>
        </div>
        <div class="row">
          <div class="col-lg-4 col-md-6">
            <div class="member" data-aos="fade-up" data-aos-delay="100">
              <img src={require('../assets/img/team/team-1.jpg')} className="img-fluid" alt="" />
              <div class="member-info">
                <div class="member-info-content">
                  <h4>Walter White</h4>
                  <span>Chief Executive Officer</span>
                </div>
                <div class="social">
                  <a href=""><i class="bi bi-twitter-x"></i></a>
                  <a href=""><i class="bi bi-facebook"></i></a>
                  <a href=""><i class="bi bi-instagram"></i></a>
                  <a href=""><i class="bi bi-linkedin"></i></a>
                </div>
              </div>
            </div>
          </div>
          // Add more agents from the HTML
        </div>
      </div>
    </section>
  );
}

export default Agents;