import React from 'react';
import Hero from './Hero';
import FeaturedProperties from './FeaturedProperties'; // New
import Services from './Services'; // Reuse full Services but trim for home
import Agents from './Agents'; // Reuse
import Testimonials from './Testimonials'; // New
import About from './About';

function Home() {
  return (
    <>
      <Hero />
      <FeaturedProperties />
      <Services isHomeVersion={true} />
      <Agents isHomeVersion={true} />
      <Testimonials />
      {/* <About /> */}
    </>
  );
}

export default Home;