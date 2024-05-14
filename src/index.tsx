//@ts-ignore
import { NativeModules, Platform } from 'react-native';

const LINKING_ERROR =
  `The package 'react-native-jsonwebtoken' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo Go\n';

const Jsonwebtoken = NativeModules.Jsonwebtoken
  ? NativeModules.Jsonwebtoken
  : new Proxy(
      {},
      {
        get() {
          throw new Error(LINKING_ERROR);
        },
      }
    );

export function verifyToken(token: String, publicKeyPEM: String): Promise<boolean> {
  return Jsonwebtoken.verifyToken(token, publicKeyPEM);
}
export function generateSecretKey(): Promise<EncryptionResult> {
  return Jsonwebtoken.generateSecretKey();
}
export function encryptData(data: String, publicKeyPEM: String): Promise<string> {
  return Jsonwebtoken.encryptData(data, publicKeyPEM);
}
