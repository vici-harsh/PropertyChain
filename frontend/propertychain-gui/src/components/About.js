import React from 'react';

function About() {
  return (
    <section id="about" className="about section-bg">
      <div class="container">
        <div class="section-title">
          <h2>About</h2>
          <p>Odio et unde deleniti. Deserunt numquam exercitationem.</p>
        </div>
        <div class="row">
          <div class="col-lg-6">
            <img src={require('../assets/img/about-company-1.jpg')} className="img-fluid" alt="" />
          </div>
          <div class="col-lg-6 pt-4 pt-lg-0 content">
            <h3>Who We Are</h3>
            <p class="fst-italic">
              Unleashing Potential with Creative Strategy
            </p>
            <p>
              Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
            </p>
            <ul>
              <li><i class="bi bi-check-circle"></i> Ullamco laboris nisi ut aliquip ex ea commodo consequat.</li>
              <li><i class="bi bi-check-circle"></i> Duis aute irure dolor in reprehenderit in voluptate velit.</li>
              <li><i class="bi bi-check-circle"></i> Ullamco laboris nisi ut aliquip ex ea commodo consequat.</li>
            </ul>
            <p>
              Ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum
            </p>
          </div>
        </div>
      </div>
    </section>
  );
}

export default About;