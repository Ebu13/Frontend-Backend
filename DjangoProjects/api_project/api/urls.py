from django.urls import path
from .views import kullanici_listesi

urlpatterns = [
    path('kullanicilar/', kullanici_listesi, name='kullanici_listesi'),
]
