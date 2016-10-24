#!/bin/bash

TARGET_BRANCH="gh-pages"
#TRAVIS_JOB_NUMBER=10.1
#TRAVIS_BUILD_NUMBER=10
#TRAVIS_PULL_REQUEST="false"
#if [ "$TRAVIS_PULL_REQUEST" != "false" ]; then
#  # In a PR
#  exit 0
#fi
REPO=`git config remote.origin.url`
git clone $REPO out
cd out
git checkout $TARGET_BRANCH || git checkout --orphan $TARGET_BRANCH
git config user.name "Travis CI"
git config user.email "$COMMIT_AUTHOR_EMAIL"
mkdir -p "builds/"
mv ../code/cb2/build/reports/tests/ "builds/$TRAVIS_JOB_NUMBER"

cat <<EOT > index.html
<html>
<head>
<link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
<h2>Last Builds</h2>
<ul>
EOT
for ((i=TRAVIS_BUILD_NUMBER; i>=$TRAVIS_BUILD_NUMBER-10; i--)) do
  echo "<li><a href=\"builds/$i.1\">$i</a></li>" >> index.html
done
cat <<EOT >> index.html
</ul>
</body>
</html>
EOT

git add builds index.html
git commit -m "Publishing report for build $TRAVIS_JOB_NUMBER"
git push origin $TARGET_BRANCH

echo "Build report uploaded to https://simonsmiley.github.io/cb2/builds/$TRAVIS_JOB_NUMBER"
