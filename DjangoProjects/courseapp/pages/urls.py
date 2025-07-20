from django.urls import path
from . import views

urlpatterns = [
    path('', views.nul),
    path('home', views.home),
    path('projects', views.projects),
    path('skills',views.skills),
    path('education',views.education),
]