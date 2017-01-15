#version 120

uniform sampler2D sampler;

uniform vec4 color;

varying vec2 texCoords;

void main() {
	//gl_FragColor = texture2D(sampler, texCoords);
	gl_FragColor = color;
}