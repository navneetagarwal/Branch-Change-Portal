# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations
import login.models


class Migration(migrations.Migration):

    dependencies = [
        ('login', '0001_initial'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='detail',
            name='PrefList',
        ),
        migrations.AddField(
            model_name='detail',
            name='Preference16',
            field=models.CharField(default=b'null', max_length=15, choices=[(b'null', b'--'), (b'AE B.Tech', b'AE B.Tech'), (b'CE B.Tech', b'CE B.Tech'), (b'CH', b'CH'), (b'CL B.Tech', b'CL B.Tech'), (b'CL Dual Deg', b'CL Dual Deg'), (b'CS B.Tech', b'CS B.Tech'), (b'EE B.Tech', b'EE B.Tech'), (b'EE Dual Deg E1', b'EE Dual Deg E1'), (b'EE Dual Deg E2', b'EE Dual Deg E2'), (b'EN Dual Deg', b'EN Dual Deg'), (b'EP B.Tech', b'EP B.Tech'), (b'EP Dual Deg N1', b'EP Dual Deg N1'), (b'ME B.Tech', b'ME B.Tech'), (b'ME Dual Deg M2', b'ME Dual Deg M2'), (b'MM B.Tech', b'MM B.Tech'), (b'MM Dual Deg Y1', b'MM Dual Deg Y1'), (b'MM Dual Deg Y2', b'MM Dual Deg Y2')], validators=[login.models.validate_Preference]),
        ),
    ]
