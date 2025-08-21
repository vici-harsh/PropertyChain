import AOS from 'aos';
import { Swiper } from 'swiper';
import '@srexi/purecounterjs';
import $ from 'jquery';

// Initialize AOS for animations
export function initAOS() {
  AOS.init({
    duration: 600,
    easing: 'ease-in-out',
    once: true,
    mirror: false
  });
  window.addEventListener('load', AOS.refresh);
  return () => window.removeEventListener('load', AOS.refresh);
}

// Custom function for Swiper with custom pagination (placeholder)
export function initSwiperWithCustomPagination(swiperElement, config) {
  // Placeholder implementation; enhance if template defines specific pagination
  return new Swiper(swiperElement, config);
}

// Initialize Swiper sliders
export function initSwiperSliders() {
  document.querySelectorAll(".init-swiper").forEach(function(swiperElement) {
    let config = JSON.parse(swiperElement.querySelector(".swiper-config")?.innerHTML.trim() || '{}');
    if (swiperElement.classList.contains("swiper-tab")) {
      initSwiperWithCustomPagination(swiperElement, config);
    } else {
      new Swiper(swiperElement, config);
    }
  });
}

// Initialize PureCounter for animated counters
export function initPureCounter() {
  if (typeof PureCounter !== 'undefined') {
    new PureCounter(); // Use global PureCounter from @srexi/purecounterjs
  } else {
    console.warn('PureCounter is not available. Ensure @srexi/purecounterjs is loaded.');
  }
}

// Toggle scrolled class on body based on scroll position
export function toggleScrolled() {
  const selectBody = document.querySelector('body');
  const selectHeader = document.querySelector('#header');
  if (!selectHeader?.classList.contains('scroll-up-sticky') && !selectHeader?.classList.contains('sticky-top') && !selectHeader?.classList.contains('fixed-top')) return;
  window.scrollY > 100 ? selectBody.classList.add('scrolled') : selectBody.classList.remove('scrolled');
}

// Toggle mobile navigation
export function mobileNavToggle() {
  const body = document.querySelector('body');
  if (body) {
    body.classList.toggle('mobile-nav-active');
    const mobileNavToggleBtn = document.querySelector('.mobile-nav-toggle');
    if (mobileNavToggleBtn) {
      mobileNavToggleBtn.classList.toggle('bi-list');
      mobileNavToggleBtn.classList.toggle('bi-x');
    }
  }
}

// Auto generate carousel indicators
export function initCarouselIndicators() {
  document.querySelectorAll('.carousel-indicators').forEach((carouselIndicator) => {
    const carousel = carouselIndicator.closest('.carousel');
    if (carousel) {
      carousel.querySelectorAll('.carousel-item').forEach((carouselItem, index) => {
        if (index === 0) {
          carouselIndicator.innerHTML += `<li data-bs-target="#${carousel.id}" data-bs-slide-to="${index}" class="active"></li>`;
        } else {
          carouselIndicator.innerHTML += `<li data-bs-target="#${carousel.id}" data-bs-slide-to="${index}"></li>`;
        }
      });
    }
  });
}

// Toggle scroll-top button visibility
export function toggleScrollTop() {
  const scrollTop = document.querySelector('.scroll-top');
  if (scrollTop) {
    window.scrollY > 100 ? scrollTop.classList.add('active') : scrollTop.classList.remove('active');
  }
  if (scrollTop) {
    scrollTop.addEventListener('click', (e) => {
      e.preventDefault();
      window.scrollTo({
        top: 0,
        behavior: 'smooth'
      });
    });
  }
}

// Preloader removal
export function removePreloader() {
  const preloader = document.querySelector('#preloader');
  if (preloader) {
    preloader.remove();
  }
}