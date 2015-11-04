from django import forms

from .models import Detail

class DetailForm(forms.ModelForm):

    class Meta:
        model = Detail
        fields = ('RollNo', 'Name', 'Current_Branch', 'CPI', 'Category', 'Preference1', 'Preference2', 'Preference3', 'Preference4', 'Preference5', 'Preference6', 
        	'Preference7', 'Preference8', 'Preference9', 'Preference10', 'Preference11', 'Preference12', 'Preference13', 'Preference14', 'Preference15', 'Preference16')
