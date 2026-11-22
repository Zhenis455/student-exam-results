#!/usr/bin/env bash
set -euo pipefail
ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
echo 'Student Exam Results запускается...'
echo 'Адрес сайта: http://localhost:8080/students'
exec "$ROOT/scripts/gradle-bootstrap.sh" bootRun
