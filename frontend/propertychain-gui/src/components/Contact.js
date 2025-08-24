import React, { useState } from 'react';

function Contact() {
  const [name, setName] = useState('');
  const [email, setEmail] = useState('');
  const [subject, setSubject] = useState('');
  const [message, setMessage] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    // Add API call or logic
    alert('Message sent!');
  };

  return (
    <section id="contact" className="contact">
      <div className="container">
        <div className="section-title">
          <h2>Contact</h2>
          <p>Odio et unde deleniti...</p>
        </div>
        <div className="row">
          <div className="col-lg-4">
            <div className="info-box">
              <i className="bi bi-geo-alt"></i>
              <h3>Address</h3>
              <p>A108 Adam Street, New York, NY 535022</p>
            </div>
            {/* Add call, email boxes */}
          </div>
          <div className="col-lg-8 mt-5 mt-lg-0">
            <form onSubmit={handleSubmit} className="php-email-form">
              <div className="row">
                <div className="col-md-6 form-group">
                  <input type="text" name="name" className="form-control" placeholder="Your Name" value={name} onChange={(e) => setName(e.target.value)} required />
                </div>
                <div className="col-md-6 form-group mt-3 mt-md-0">
                  <input type="email" className="form-control" name="email" placeholder="Your Email" value={email} onChange={(e) => setEmail(e.target.value)} required />
                </div>
              </div>
              <div className="form-group mt-3">
                <input type="text" className="form-control" name="subject" placeholder="Subject" value={subject} onChange={(e) => setSubject(e.target.value)} required />
              </div>
              <div className="form-group mt-3">
                <textarea className="form-control" name="message" rows="5" placeholder="Message" value={message} onChange={(e) => setMessage(e.target.value)} required></textarea>
              </div>
              <div className="my-3">
                <div className="loading">Loading</div>
                <div className="error-message"></div>
                <div className="sent-message">Your message has been sent. Thank you!</div>
              </div>
              <div className="text-center"><button type="submit">Send Message</button></div>
            </form>
          </div>
        </div>
      </div>
    </section>
  );
}

export default Contact;