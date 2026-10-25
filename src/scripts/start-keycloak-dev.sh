#!/usr/bin/env sh
# Navigate to project root (2 directories up from scripts/)
PROJECT_ROOT="$(cd "$(dirname "$0")/../.." && pwd)"

# Laad het .env bestand in als variabelen
if [ -f "$PROJECT_ROOT/.env" ]; then
  export $(grep -v '^#' "$PROJECT_ROOT/.env" | xargs)
else
  echo "Let op: Geen .env bestand gevonden op $PROJECT_ROOT/.env"
fi
set -eu

KEYCLOAK_HOME=${KEYCLOAK_HOME:-"C:/CODING PROJECTS and WEBSITES/TOOLS/keycloak-26.6.4/keycloak-26.6.4"}
KEYCLOAK_PORT=${KEYCLOAK_PORT:-9090}

if [ ! -f "$KEYCLOAK_HOME/bin/kc.sh" ]; then
  if [ -f "$KEYCLOAK_HOME/keycloak-26.6.4/bin/kc.sh" ]; then
    echo "Keycloak staat waarschijnlijk een map te diep uitgepakt:"
    echo "$KEYCLOAK_HOME/keycloak-26.6.4"
    echo
    echo "Verplaats de inhoud van die binnenste map naar:"
    echo "$KEYCLOAK_HOME"
    echo
    echo "Of zet KEYCLOAK_HOME expliciet naar de binnenste map."
    exit 1
  fi

  echo "Keycloak is niet gevonden op:"
  echo "$KEYCLOAK_HOME"
  echo
  echo "Zet KEYCLOAK_HOME naar je lokale Keycloak-map, bijvoorbeeld:"
  echo "KEYCLOAK_HOME=/opt/keycloak-26.6.4 ./scripts/start-keycloak-dev.sh"
  exit 1
fi

if [ ! -d "$KEYCLOAK_HOME/lib/lib/boot" ]; then
  echo "Keycloak boot-libraries ontbreken:"
  echo "$KEYCLOAK_HOME/lib/lib/boot"
  echo
  echo "Waarschijnlijk is de Keycloak installatie incompleet. Pak de distributie opnieuw uit."
  exit 1
fi

echo "Keycloak starten op http://localhost:$KEYCLOAK_PORT"
echo "Stoppen kan met Ctrl+C in dit venster."
echo

cd "$KEYCLOAK_HOME"
exec ./bin/kc.sh start-dev --http-port="$KEYCLOAK_PORT"
