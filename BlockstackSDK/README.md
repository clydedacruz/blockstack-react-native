
# react-native-blockstack [![npm version](https://img.shields.io/npm/v/react-native-blockstack.svg)](https://www.npmjs.com/package/react-native-blockstack)
*Decentralized identity and storage with Blockstack in React Native*
## Getting started

`$ npm install react-native-blockstack --save`

### Mostly automatic installation (not for iOS, see steps below)

`$ react-native link react-native-blockstack

### Manual installation


#### iOS

##### Setup Cocoapods
1. Install cocoapods, `sudo gem install cocoapods`
2. In the terminal, navigate to your project root and use command `pod init`
3. In the created `Podfile`, add the following lines:
    ```
    use_frameworks!

    pod 'Blockstack'
    pod 'React', :path => '../node_modules/react-native', :subspecs => [
        'Core',
        'CxxBridge', # Include this for RN >= 0.47
        'DevSupport', # Include this to enable In-App Devmenu if RN >= 0.43
        'RCTText',
        'RCTNetwork',
        'RCTWebSocket', # Needed for debugging
        # Add any other subspecs you want to use in your project
    ]
    # Explicitly include Yoga if you are using RN >= 0.42.0
    pod 'yoga', :path => '../node_modules/react-native/ReactCommon/yoga'
    # Third party deps podspec link
    pod 'DoubleConversion', :podspec => '../node_modules/react-native/third-party-podspecs/DoubleConversion.podspec'
    pod 'glog', :podspec => '../node_modules/react-native/third-party-podspecs/glog.podspec'
    pod 'Folly', :podspec => '../node_modules/react-native/third-party-podspecs/Folly.podspec'
    ```
4. From the terminal, enter `pod install`
5. Open the newly created `[your project's name].xcworkspace` project. Note: Always use this file and not the .xcodeproj file when opening your project.

##### Add module
1. In XCode, in the project navigator, right click your project and select `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-blockstack` ➜ `ios` and add the `RNBlockstackSdk` folder
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.reactlibrary.RNBlockstackSdkPackage;` to the imports at the top of the file
  - Add `new RNBlockstackSdkPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-blockstack'
  	project(':react-native-blockstack').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-blockstack/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-blockstack')
  	```

#### Windows
[Read it! :D](https://github.com/ReactWindows/react-native)

1. In Visual Studio add the `RNBlockstackSdk.sln` in `node_modules/react-native-blockstack/windows/RNBlockstackSdk.sln` folder to their solution, reference from their app.
2. Open up your `MainPage.cs` app
  - Add `using Blockstack.Sdk.RNBlockstackSdk;` to the usings at the top of the file
  - Add `new RNBlockstackSdkPackage()` to the `List<IReactPackage>` returned by the `Packages` method


## Usage
```javascript
import RNBlockstackSdk from 'react-native-blockstack';

// check whether a blockstack session is running
result = await RNBlockstackSdk.hasSession()
hasSession = result["hasSession"]

// create a new session (replace with your app domain)
config = {
      appDomain:"https://example.com",
      scopes:["store_write"]
}
session = await RNBlockstackSdk.createSession(config)

// sign user in (starts redirect to blockstack browser)
await RNBlockstackSdk.signIn()

// handle auth response, e.g. from a prop
result = await RNBlockstackSdk.handleAuthResponse(this.props.authResponse)
did = result["decentralizedID"]

// sign user out
result = await RNBlockstackSdk.signUserOut()
success = result["signedOut"]

// check whether a user is signed in
result = await RNBlockstackSdk.isUserSignedIn()
signedIn["signedIn"]

// read profile data of signed in user
result = await RNBlockstackSdk.loadUserData()
did = result["decentralizedID"]
                
// put file, e.g. store unencrypted text in "text.txt"
content = "Hello React Native"
options = {encrypt: false}
result = await RNBlockstackSdk.putFile("text.txt", content, options)
fileUrl = result["fileUrl"]

// get file, e.g. read unencrypted text from "text.txt"
options = {decrypt:false}
result = await RNBlockstackSdk.getFile("text.txt", options)
fileContents = result["fileContents"]

```
