from django.conf.urls import include, url
from . import views

urlpatterns = [
    url(r'^detail/new/$', views.detail_new, name='detail_new'),
    url(r'^$', views.detail_list, name='detail_list'),
    url(r'^detail/(?P<pk>\w+)/$', views.detail_detail, name='detail_detail'),
    url(r'^detail/(?P<pk>\w+)/edit/$', views.detail_edit, name='detail_edit'),
    url(r'^newuser/$', views.adduser, name='adduser'),
]
