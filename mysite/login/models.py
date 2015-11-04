# -*- coding: utf-8 -*-

from django.db import models
from django.core.exceptions import ValidationError
from django.contrib import admin
from django.contrib.auth.models import User
import string
import os
import csv

detail_data = []
roll_row = -1
with open('./input/input.csv','r') as detail:
  read = csv.reader(detail, delimiter=',')
  for row in read:
    detail_data.append(row)

val_prog = []
null_bool = 0

def get_random_string(length, stringset=string.ascii_letters):
    return ''.join([stringset[i % len(stringset)] for i in [ord(x) for x in os.urandom(length)]])

def validate_CPI(value):
  global roll_row
  if value<0:
    raise ValidationError('%.2f is not a valid CPI. It has to be greater than 0.' % value)
  if value>10:
    raise ValidationError('%.2f is not a valid CPI. It has to be less than 10.' % value)
  if float(value) != float(detail_data[roll_row][3]):
      raise ValidationError('CPI is not the same as in input file.')

def validate_Roll(value):
    global val_prog
    global null_bool
    null_bool = 0
    if(((str(value))[:2]) != '15'):
      raise ValidationError('%s is not a valid roll number. It should be of form 15XXXXXXX.' % value)
    elif(len(str(value))!=9):
      raise ValidationError('%s is not a valid roll number. The length should be 9.' % value)
    roll = -1
    for x in range(len(detail_data)):
      if str(value) == str(detail_data[x][0]):
        roll = x
    global roll_row
    roll_row = roll
    if roll == -1:
      raise ValidationError('Roll No is not in the input file.')
    for x in range(len(val_prog)):
      val_prog[x][1] = 0

def validate_Branch(value):
  global roll_row
  global val_prog
  if str(value) != str(detail_data[roll_row][2]):
    raise ValidationError('Current Branch is not the same as in input file.')
  x = [x for x in val_prog if value in x][0]
  index = val_prog.index(x)
  if val_prog[index][1]==0:
    val_prog[index][1]=2

def validate_Category(value):
  global roll_row
  if str(value) != str(detail_data[roll_row][4]):
    raise ValidationError('Category is not the same as in input file.')

def validate_Preference(value):
  global null_bool
  if value != 'null':
    if null_bool == 0:
      global val_prog
      x = [x for x in val_prog if value in x][0]
      index = val_prog.index(x)
      if val_prog[index][1] == 0:
        val_prog[index][1] = 1
      elif val_prog[index][1] == 1:
        raise ValidationError('%s is repeated. Please make necessary changes.' % value)
      elif val_prog[index][1] == 2:
        raise ValidationError('%s is your original branch. You can not add your original branch.' % value)
    else:
      raise ValidationError("You must have declared null before in the preference list. Not allowed.")
  else:
    null_bool = 1

class Detail(models.Model):
    author = models.ForeignKey('auth.User', unique = True, default='', help_text='Enter the proper Roll No provided by the institute.')
    RollNo = models.CharField(primary_key=True, validators=[validate_Roll], max_length=9)
    Name = models.CharField(max_length=80)
    prog_data = []
    with open('./input/input_programmes.csv', 'r') as csvfile:
      reader = csv.reader(csvfile, delimiter = ',')
      for row in reader:
        prog_data.append(row)

    global val_prog
    Choices = ()
    for x in range(len(prog_data)):
      Choices = ((prog_data[x][0], prog_data[x][0]),) + Choices
      val_prog.append([prog_data[x][0],0])
    Choices = Choices[::-1]

    Cat_Choices = (('GE', 'GE'), ('OBC','OBC'), ('ST','ST'), ('SC','SC'),)

    Current_Branch = models.CharField(max_length = 15,choices=Choices,default='',validators=[validate_Branch])
    CPI = models.DecimalField(max_digits=4, decimal_places=2, default = 0.00, validators=[validate_CPI])
    Category = models.CharField(max_length=4,choices=Cat_Choices,default='GE',validators=[validate_Category])

    Choices = (('null', '--'),) +   Choices 

    Preference1 = models.CharField(max_length = 15,choices=Choices,default='null',validators=[validate_Preference])
    Preference2 = models.CharField(max_length = 15,choices=Choices,default='null',validators=[validate_Preference])
    Preference3 = models.CharField(max_length = 15,choices=Choices,default='null',validators=[validate_Preference])
    Preference4 = models.CharField(max_length = 15,choices=Choices,default='null',validators=[validate_Preference])
    Preference5 = models.CharField(max_length = 15,choices=Choices,default='null',validators=[validate_Preference])
    Preference6 = models.CharField(max_length = 15,choices=Choices,default='null',validators=[validate_Preference])
    Preference7 = models.CharField(max_length = 15,choices=Choices,default='null',validators=[validate_Preference])
    Preference8 = models.CharField(max_length = 15,choices=Choices,default='null',validators=[validate_Preference])
    Preference9 = models.CharField(max_length = 15,choices=Choices,default='null',validators=[validate_Preference])
    Preference10 = models.CharField(max_length = 15,choices=Choices,default='null',validators=[validate_Preference])
    Preference11 = models.CharField(max_length = 15,choices=Choices,default='null',validators=[validate_Preference])
    Preference12 = models.CharField(max_length = 15,choices=Choices,default='null',validators=[validate_Preference])
    Preference13 = models.CharField(max_length = 15,choices=Choices,default='null',validators=[validate_Preference])
    Preference14 = models.CharField(max_length = 15,choices=Choices,default='null',validators=[validate_Preference])
    Preference15 = models.CharField(max_length = 15,choices=Choices,default='null',validators=[validate_Preference])
    Preference16 = models.CharField(max_length = 15,choices=Choices,default='null',validators=[validate_Preference])

class DetailAdmin(admin.ModelAdmin):
      actions = ['download_csv','apply_algorithm','import_data']
      list_display = ('RollNo', 'Name','Current_Branch','CPI','Category',)
      def download_csv(self, request, queryset):
        import csv
        # from django.http import HttpResponse
        f = open('./input/input_options.csv', 'wb')
        writer = csv.writer(f)
        # writer.writerow(['RollNo', 'Name','Current_Branch','CPI','Preference1','Preference2','Preference3','Preference4','Preference5','Preference6','Preference7','Preference8','Preference9','Preference10','Preference11','Preference12','Preference13','Preference14','Preference15','Preference16'])
        for s in queryset:
          if str(s.RollNo) != '150000000':
            writer.writerow([s.RollNo,s.Name,s.Current_Branch,s.CPI,s.Category,s.Preference1,s.Preference2,s.Preference3,s.Preference4,s.Preference5,s.Preference6,s.Preference7,s.Preference8,s.Preference9,s.Preference10,s.Preference11,s.Preference12,s.Preference13,s.Preference14,s.Preference15,s.Preference16])
        f.close()

        # f = open('yes.csv', 'r')
        # response = HttpResponse(f, content_type='text/csv')
        # response['Content-Disposition'] = 'attachment; filename=stat-info.csv'
        # return response
      def apply_algorithm(self, request, queryset):
        import subprocess
        java_file='Main'
        javas='./Main.java'
        rcmd='javac '+javas
        proc=subprocess.Popen(rcmd,shell=True)
        proc.wait()
        cmd = 'java ' + java_file 
        proc = subprocess.Popen(cmd, shell=True)

      def import_data(self, request, queryset):
        for detail in Detail.objects.all():
          if detail.Name != "new user":
            detail.delete()
        f=open('./input/input_options.csv','r')
        usr=Detail.objects.all()
        dataReader=csv.reader(f)
        for row in dataReader:
          det=Detail()
          new_user=User.objects.create(username=get_random_string(16),password='setnew',first_name=get_random_string(16),last_name=get_random_string(16))
          new_user.save()
          det.author=new_user
          det.RollNo=row[0]
          det.Name=row[1]
          det.Current_Branch=row[2]
          det.CPI=row[3]
          det.Category=row[4]
          if len(row)>5 :
            det.Preference1=row[5]
            if len(row)>6 :
              det.Preference2=row[6]
              if len(row)>7 :
                det.Preference3=row[7]
                if len(row)>8 :
                  det.Preference4=row[8]
                  if len(row)>9 :
                    det.Preference5=row[9]
                    if len(row)>10 :
                      det.Preference6=row[10]
                      if len(row)>11 :
                        det.Preference7=row[11]
                        if len(row)>12 :
                          det.Preference8=row[12]
                          if len(row)>13 :
                            det.Preference9=row[13]
                            if len(row)>14 :
                              det.Preference10=row[14]
                              if len(row)>15 :
                                det.Preference11=row[15]
                                if len(row)>16 :
                                  det.Preference12=row[16]
                                  if len(row)>17 :  
                                    det.Preference13=row[17]
                                    if len(row)>18 :
                                      det.Preference14=row[18]
                                      if len(row)>19 :
                                        det.Preference15=row[19]
                                        if len(row)>20 :
                                          det.Preference16=row[20]
          det.save()

admin.site.register(Detail,DetailAdmin)