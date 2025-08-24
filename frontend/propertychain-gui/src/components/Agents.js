import React from 'react';

function Agents({ isHomeVersion = false }) {
  return (
    <section id="agents" className="agents section-bg">
      <div className="container">
        <div className="section-title">
          <h2>{isHomeVersion ? 'Our Agents' : 'Agents'}</h2>
          <p>Necessitatibus eius consequatur ex aliquid fuga eum quidem sint consectetur velit</p>
        </div>
        <div className="row">
          <div className="col-lg-4 col-md-6">
            <div className="member">
              <div className="pic">
                <img src="/assets/img/team/team-1.jpg" alt="Agent 1" />
              </div>
              <div className="member-info">
                <h4>Harsh Purohit</h4>
                <span>Chief Executive Officer</span>
                <div className="social">
                  <a href="#"><i className="bi bi-twitter-x"></i></a>
                </div>
              </div>
            </div>
          </div>
          <div className="col-lg-4 col-md-6">
            <div className="member">
              <div className="pic">
                <img src="/assets/img/team/team-2.jpg" alt="Agent 2" />
              </div>
              <div className="member-info">
                <h4>Smit Patel</h4>
                <span>Senior Real Estate Agent</span>
                <div className="social">
                  <a href="#"><i className="bi bi-twitter-x"></i></a>
                </div>
              </div>
            </div>
          </div>
          <div className="col-lg-4 col-md-6">
            <div className="member">
              <div className="pic">
                <img src="/assets/img/team/team-3.jpg" alt="Agent 3" />
              </div>
              <div className="member-info">
                <h4>Hari</h4>
                <span>Property Manager</span>
                <div className="social">
                  <a href="#"><i className="bi bi-twitter-x"></i></a>
                </div>
              </div>
            </div>
          </div>
          <div className="col-lg-4 col-md-6">
            <div className="member">
              <div className="pic">
                <img src="/assets/img/team/team-4.jpg" alt="Agent 4" />
              </div>
              <div className="member-info">
                <h4>OMPrakash</h4>
                <span>Real Estate Consultant</span>
                <div className="social">
                  <a href="#"><i className="bi bi-twitter-x"></i></a>
                </div>
              </div>
            </div>
          </div>
          <div className="col-lg-4 col-md-6">
            <div className="member">
              <div className="pic">
                <img src="/assets/img/team/team-5.jpg" alt="Agent 5" />
              </div>
              <div className="member-info">
                <h4>Aviral</h4>
                <span>Lead Broker</span>
                <div className="social">
                  <a href="#"><i className="bi bi-twitter-x"></i></a>
                </div>
              </div>
            </div>
          </div>
          <div className="col-lg-4 col-md-6">
            <div className="member">
              <div className="pic">
                <img src="/assets/img/team/team-6.jpg" alt="Agent 6" />
              </div>
              <div className="member-info">
                <h4>Shrey</h4>
                <span>Business Development Manager</span>
                <div className="social">
                  <a href="#"><i className="bi bi-twitter-x"></i></a>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
  );
}

export default Agents;