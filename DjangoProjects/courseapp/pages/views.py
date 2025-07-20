from django.http import HttpResponse
from django.shortcuts import redirect, render

def nul(request):
    return redirect('/home')

def home(request):
    return render(request,'home.html')

def projects(request):
    return render(request,'projects.html')

def skills(request):
    return render(request,'skills.html')

def education(request):
    return render(request,'education.html')