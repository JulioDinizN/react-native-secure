import { NativeModules, Platform } from 'react-native';

const LINKING_ERROR =
  `The package 'react-native-secure' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo Go\n';

// @ts-expect-error
const isTurboModuleEnabled = global.__turboModuleProxy != null;

const SecureModule = isTurboModuleEnabled
  ? require('./NativeSecure').default
  : NativeModules.Secure;

const Secure = SecureModule
  ? SecureModule
  : new Proxy(
      {},
      {
        get() {
          throw new Error(LINKING_ERROR);
        },
      }
    );

export function multiply(a: number, b: number): Promise<number> {
  return Secure.multiply(a, b);
}

export function requireLocalAuth(): void {
  return Secure.requireLocalAuth();
}

export function canAuthenticate(): void {
  return Secure.canAuthenticate();
}
