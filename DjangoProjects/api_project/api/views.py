from django.http import JsonResponse
from .models import Kullanici

def kullanici_listesi(request):
    kullanici_listesi = Kullanici.objects.all()
    data = {
       'kullanicilar': list(kullanici_listesi.values())
    }
    return JsonResponse(data)
