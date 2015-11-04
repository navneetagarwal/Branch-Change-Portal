# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations
import login.models


class Migration(migrations.Migration):

    dependencies = [
        ('login', '0003_auto_20151029_1837'),
    ]

    operations = [
        migrations.AlterField(
            model_name='detail',
            name='RollNo',
            field=models.CharField(max_length=9, serialize=False, primary_key=True, validators=[login.models.validate_Roll]),
        ),
    ]
