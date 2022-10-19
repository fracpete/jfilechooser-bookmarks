RELEASE
=======

Use the following command to make a new release:

```
mvn release:clean release:prepare release:perform
```

After the release perform a:

```
git push
```

Update the Maven artifact version in [README.md](README.md#maven)