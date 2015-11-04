from django.shortcuts import render, get_object_or_404, redirect
from django.contrib.auth.decorators import login_required
from django.contrib.auth.models import User
from django.contrib.auth import login
from django.http import HttpResponseRedirect
from .models import Detail
from .forms import DetailForm
from .UserForm import UserForm

@login_required
def detail_new(request):
    if request.method == "POST":
        form = DetailForm(request.POST)
        if form.is_valid():
            detail = form.save(commit=False)
            detail.author = request.user
            detail.save()
            return redirect('login.views.detail_list')
    else:
        form = DetailForm()
    return render(request, 'login/detail_edit.html', {'form':form})

@login_required
def detail_list(request):
    details = Detail.objects.order_by('-CPI')
    return render(request, 'login/detail_list.html', {'details':details})

@login_required
def detail_detail(request, pk):
    detail = get_object_or_404(Detail, pk=pk)
    return render(request, 'login/detail_detail.html',{'detail':detail})

@login_required
def detail_edit(request, pk):
    detail = get_object_or_404(Detail, pk=pk)
    if request.method == "POST":
        form = DetailForm(request.POST, instance=detail)
        if form.is_valid():
            detail = form.save(commit=False)
            detail.author = request.user
            detail.save()
            return redirect('login.views.detail_list')
    else:
        form = DetailForm(instance=detail)
    return render(request, 'login/detail_edit.html', {'form':form})

def adduser(request):
    if request.method == "POST":
        form = UserForm(request.POST)
        if form.is_valid():
            new_user = User.objects.create_user(**form.cleaned_data)
            
            # redirect, or however you want to get to the main view
            return redirect('login.views.detail_list')
    else:
        form = UserForm() 

    return render(request, 'login/adduser.html', {'form': form}) 
