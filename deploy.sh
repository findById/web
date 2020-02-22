#!/usr/bin/env bash
# deploy
case "$1" in
    -h)
        echo "Usage: $0 {install|upload}"
        exit $?
    ;;
    install)
        ./gradlew :web-core:install
        ./gradlew :web-rbac:install
        ./gradlew :web-job:install
    ;;
    upload)
        ./gradlew :web-core:uploadArchives
        ./gradlew :web-rbac:uploadArchives
        ./gradlew :web-job:uploadArchives
    ;;
esac
