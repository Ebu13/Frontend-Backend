from django.db import models

class Kullanici(models.Model):
    ad = models.CharField(max_length=50)
    soyad = models.CharField(max_length=50)
    sifre = models.CharField(max_length=50)
    yas = models.IntegerField()
