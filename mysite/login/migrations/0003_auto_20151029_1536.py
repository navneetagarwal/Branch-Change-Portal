# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations
from django.conf import settings


class Migration(migrations.Migration):

    dependencies = [
        ('login', '0002_auto_20151028_1757'),
    ]

    operations = [
        migrations.AlterField(
            model_name='detail',
            name='author',
            field=models.ForeignKey(default=b'', to=settings.AUTH_USER_MODEL, help_text=b'Enter the proper Roll No provided by the institute.', unique=True),
        ),
    ]
