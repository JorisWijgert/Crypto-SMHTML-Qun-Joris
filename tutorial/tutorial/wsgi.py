"""
WSGI config for tutorial project.

It exposes the WSGI callable as a module-level variable named ``application``.

For more information on this file, see
https://docs.djangoproject.com/en/1.11/howto/deployment/wsgi/
"""

import os

from django.core.wsgi import get_wsgi_application

os.environ.setdefault("DJANGO_SETTINGS_MODULE", "tutorial.settings")

application = get_wsgi_application()

ALLOWED_HOSTS = ['0.0.0.0', 'xx.xx.xx.xx', '192.168.227.128']