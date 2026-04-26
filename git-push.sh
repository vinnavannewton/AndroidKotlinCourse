#!/bin/bash
echo "Staging all changes..."
git add .

echo "Committing with message: Update"
git commit -m "Update"

echo "Pushing to remote..."
git push
