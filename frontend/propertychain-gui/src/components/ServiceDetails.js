import React from 'react';

function ServiceDetails() {
  return (
    <section id="service-details" className="service-details">
      <div class="container">
        <div class="row gy-4">
          <div class="col-lg-4">
            <div class="services-list">
              <a href="#">Web Design</a>
              <a href="#">Product Management</a>
              // Add more from HTML
            </div>
            <h4>Enim quis est voluptatibus aliquid consequatur fugiat</h4>
            <p>Esse voluptas cumque vel exercitationem. Reiciendis est hic accusamus. Non ipsam et sed minima temporibus laudantium. Soluta voluptate sed facere corporis dolores excepturi</p>
          </div>
          <div class="col-lg-8">
            <img src={require('../assets/img/services.jpg')} className="img-fluid" alt="" />
            <h3>Temporibus et in vero dicta aut eius lidero plastis trand lined voluptas dolorem ut voluptas</h3>
            <p>Blanditiis voluptate odit ex error ea sed officiis deserunt. Cupiditate non consequatur et doloremque consequuntur. Accusantium labore reprehenderit error temporibus saepe perferendis fuga doloribus vero. Qui omnis quo sit. Dolorem architecto eum et quos deleniti officia</p>
            <ul>
              <li><i class="bi bi-check-circle"></i> <span>Aut eum totam accusantium voluptatem.</span></li>
              <li><i class="bi bi-check-circle"></i> <span>Assumenda et porro nisi nihil nesciunt voluptatibus.</span></li>
              <li><i class="bi bi-check-circle"></i> <span>Ullamco laboris nisi ut aliquip ex ea</span></li>
            </ul>
            <p>Est reprehenderit voluptatem necessitatibus asperiores neque sed ea illo. Deleniti quam sequi optio iste veniam repellat odit. Aut pariatur itaque nesciunt fuga.</p>
            <p>Sunt rem odit accusantium omnis perspiciatis officia. Laboriosam aut consequuntur recusandae mollitia doloremque est architecto cupiditate ullam. Quia est ut occaecati fuga. Distinctio ex repellendus eveniet velit sint quia sapiente cumque. Et ipsa perferendis ut nihil. Laboriosam vel voluptates tenetur nostrum. Eaque iusto cupiditate et totam et quia dolorum in. Sunt molestiae ipsum at consequatur vero. Architecto ut pariatur autem ad non cumque nesciunt qui maxime. Sunt eum quia impedit dolore alias explicabo ea.</p>
          </div>
        </div>
      </div>
    </section>
  );
}

export default ServiceDetails;