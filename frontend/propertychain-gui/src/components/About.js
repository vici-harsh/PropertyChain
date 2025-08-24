import React from 'react';

function About() {
  return (
    <section id="about" className="about section-bg"> {/* CSS bg */}
      <div className="container">
        <div className="section-title">
          <h2>About</h2>
          <p>Odio et unde deleniti. Deserunt numquam exercitationem.</p>
        </div>
        <div className="row">
          <div className="col-lg-6">
            <img src="../assets/img/about-company-1.jpg" className="img-fluid" alt="About Company" />
          </div>
          <div className="col-lg-6 pt-4 pt-lg-0 content">
            <h3>Who We Are</h3>
            <p className="fst-italic">Unleashing Potential with Creative Strategy</p>
            <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.</p>
            <ul>
              <li><i className="bi bi-check-circle"></i> Ullamco laboris nisi ut aliquip ex ea commodo consequat.</li>
              <li><i className="bi bi-check-circle"></i> Duis aute irure dolor in reprehenderit in voluptate velit.</li>
              <li><i className="bi bi-check-circle"></i> Ullamco laboris nisi ut aliquip ex ea commodo consequat.</li>
            </ul>
            <p>Ullamco laboris nisi ut aliquip ex ea commodo consequat...</p>
          </div>
        </div>
        {/* Added counts from about.html */}
        <div className="counts section-bg">
          <div className="row">
            <div className="col-lg-3 col-md-6">
              <div className="count-box">
                <i className="bi bi-emoji-smile"></i>
                <span data-purecounter-start="0" data-purecounter-end="232" data-purecounter-duration="1" className="purecounter"></span>
                <p>Happy Clients</p>
              </div>
            </div>
            <div className="col-lg-3 col-md-6">
              <div className="count-box">
                <i className="bi bi-journal-richtext"></i>
                <span data-purecounter-start="0" data-purecounter-end="521" data-purecounter-duration="1" className="purecounter"></span>
                <p>Projects</p>
              </div>
            </div>
            <div className="col-lg-3 col-md-6">
              <div className="count-box">
                <i className="bi bi-headset"></i>
                <span data-purecounter-start="0" data-purecounter-end="1463" data-purecounter-duration="1" className="purecounter"></span>
                <p>Hours Of Support</p>
              </div>
            </div>
            <div className="col-lg-3 col-md-6">
              <div className="count-box">
                <i className="bi bi-people"></i>
                <span data-purecounter-start="0" data-purecounter-end="15" data-purecounter-duration="1" className="purecounter"></span>
                <p>Hard Workers</p>
              </div>
            </div>
          </div>
        </div>
        {/* Added skills from about.html */}
        <div className="skills section-bg">
          <div className="row skills-content">
            <div className="col-lg-6">
              <div className="progress">
                <span className="skill">Lorem Ipsum <span className="val">100%</span></span>
                <div className="progress-bar-wrap">
                  <div className="progress-bar" role="progressbar" aria-valuenow="100" aria-valuemin="0" aria-valuemax="100"></div>
                </div>
              </div>
              {/* Add more progress bars */}
            </div>
            {/* Right column similar */}
          </div>
        </div>
      </div>
    </section>
  );
}

export default About;