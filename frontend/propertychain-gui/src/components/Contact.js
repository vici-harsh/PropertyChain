import React, { useState } from 'react';

function Contact() {
  const [formData, setFormData] = useState({ name: '', email: '', subject: '', message: '' });

  const handleSubmit = (e) => {
    e.preventDefault();
    // Send form data to backend API (e.g., axios.post('/api/contact', formData))
    alert('Message Sent!');
  };

  return (
    <section id="contact" className="contact section-bg">
      <div class="container">
        <div class="section-title">
          <h2>Contact</h2>
          <p>Odio et unde deleniti. Deserunt numquam exercitationem.</p>
        </div>
        <div class="row">
          <div class="col-lg-4">
            <div class="info d-flex flex-column justify-content-center" data-aos="fade-right">
              <div class="address">
                <i class="bi bi-geo-alt"></i>
                <h4>Location:</h4>
                <p>A108 Adam Street,<br />New York, NY 535022</p>
              </div>
              <div class="email">
                <i class="bi bi-envelope"></i>
                <h4>Email:</h4>
                <p>info@example.com</p>
              </div>
              <div class="phone">
                <i class="bi bi-phone"></i>
                <h4>Call:</h4>
                <p>+1 5589 55488 55</p>
              </div>
            </div>
          </div>
          <div class="col-lg-8 mt-5 mt-lg-0" data-aos="fade-left">
            <form onSubmit={handleSubmit} className="php-email-form">
              <div class="row">
                <div class="col-md-6 form-group">
                  <input type="text" name="name" className="form-control" id="name" placeholder="Your Name" required value={formData.name} onChange={(e) => setFormData({ ...formData, name: e.target.value })} />
                </div>
                <div class="col-md-6 form-group mt-3 mt-md-0">
                  <input type="email" className="form-control" name="email" id="email" placeholder="Your Email" required value={formData.email} onChange={(e) => setFormData({ ...formData, email: e.target.value })} />
                </div>
              </div>
              <div class="form-group mt-3">
                <input type="text" className="form-control" name="subject" id="subject" placeholder="Subject" required value={formData.subject} onChange={(e) => setFormData({ ...formData, subject: e.target.value })} />
              </div>
              <div class="form-group mt-3">
                <textarea className="form-control" name="message" rows="5" placeholder="Message" required value={formData.message} onChange={(e) => setFormData({ ...formData, message: e.target.value })}></textarea>
              </div>
              <div class="my-3">
                <div class="loading">Loading</div>
                <div class="error-message"></div>
                <div class="sent-message">Your message has been sent. Thank you!</div>
              </div>
              <div class="text-center"><button type="submit">Send Message</button></div>
            </form>
          </div>
        </div>
      </div>
    </section>
  );
}

export default Contact;