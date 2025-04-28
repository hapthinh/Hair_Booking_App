/**
 * Import function triggers from their respective submodules:
 *
 * const {onCall} = require("firebase-functions/v2/https");
 * const {onDocumentWritten} = require("firebase-functions/v2/firestore");
 *
 * See a full list of supported triggers at https://firebase.google.com/docs/functions
 */

const {onRequest} = require("firebase-functions/v2/https");
const logger = require("firebase-functions/logger");

// Create and deploy your first functions                                                                                                                                              
// https://firebase.google.com/docs/functions/get-started

// exports.helloWorld = onRequest((request, response) => {
//   logger.info("Hello logs!", {structuredData: true});
//   response.send("Hello from Firebase!");
// });
const functions = require("firebase-functions");
const admin = require("firebase-admin");
admin.initializeApp();

// Cloud Function để xóa người dùng
exports.deleteUser = functions.https.onRequest(async (req, res) => {
  const uid = req.query.uid; // Lấy UID từ query string

  if (!uid) {
    return res.status(400).send("Missing UID");
  }

  try {
    // Xóa người dùng khỏi Firebase Authentication
    await admin.auth().deleteUser(uid);
    res.status(200).send("Successfully deleted user");
  } catch (error) {
    console.error("Error deleting user:", error);
    res.status(500).send("Error deleting user");
  }
});

