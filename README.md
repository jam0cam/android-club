# Handy Android Club

### Signing
    signingConfigs {
        debug {
            storeFile file('../androidclub.keystore')
            storePassword "handyrules"
            keyAlias "androidclub"
            keyPassword "handyrules"
        }
    }
**SHA-1** FA:7E:9F:F1:64:22:42:77:7D:27:31:C3:3D:09:43:25:D9:07:30:3E

If you're curious about your debug signing key you can print out the info with this command:
`keytool -exportcert -list -v -alias androiddebugkey -keystore ~/.android/debug.keystore`
or
`keytool -exportcert -list -v -alias <your-key-name> -keystore <path-to-production-keystore>`


### Cloud Messaging
**Server API KEY:** AIzaSyAtNAD3rpcF7cuck1XxA8VOIGmKMaCZjVI
**Sender ID:** 977176967003

### Analytics
**Google Analytics Account** odolejsi@handybook.com Apps
**Analytics tracking ID:** UA-70958758-1
**Analytics Property:** HandyAndroidClub Android: com.handy.androidclub
