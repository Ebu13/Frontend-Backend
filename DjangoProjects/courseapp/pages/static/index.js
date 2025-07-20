function fetchProjects() {
    // Fetching projects with GitHub API
    $.getJSON(
      "https://api.github.com/users/Ebu13/repos?type=owner&sort=updated",
      function (data) {
        var projectsContainer = $("#projectsContainer");
  
        data.forEach(function (repo) {
          var projectTitle = repo.name;
          var projectLink = repo.html_url;
          var projectDescription = repo.description;
  
          var projectHTML = `
            <div class="col-md-4 mt-4">
              <div class="card">
                <div class="card-body">
                  <h5 class="card-title">${projectTitle}</h5>
                  <p class="card-text">${projectDescription}</p>
                  <a href="${projectLink}" class="btn btn-primary">View on GitHub</a>
                </div>
              </div>
            </div>
          `;
  
          projectsContainer.append(projectHTML);
        });
      }
    );
  }
  
  // Call the fetchProjects function to execute it
  fetchProjects();
  